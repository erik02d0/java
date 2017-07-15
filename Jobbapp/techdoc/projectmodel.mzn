include "globals.mzn";

% Example parameters and initial checks:
int: l;
constraint assert(l>0, "Number of teams can't be zero");
int: wpt = 3;		  % workers per team
int: n = wpt*l;   % number of workers
int: t = l;	% number of teams
int: d=t;  		      	% number of drivers

% Set up and constrain preference matrix:
array[1..n,1..n] of var 0..n-1: P;
constraint forall(i in 1..n)(P[i,i] == 0);
constraint forall(i in 1..n)(alldifferent(col(P,i)) /\ alldifferent(row(P,i)));

% Set up and constrain shift (the partition into teams):
array[1..n] of var 1..n: shift;
constraint alldifferent(shift);

% The drivers are numbered 1..d. Choose driver for each team:
array[1..t] of var 1..d: teamDrivers;
constraint alldifferent(teamDrivers);

% Symmetry-breaking:
constraint forall(i in 2..t)(teamDrivers[i-1] < teamDrivers[i]);
constraint forall(i in 1..t, j in 2..wpt-1)(shift[wpt*(i-1)+j] < shift[wpt*(i-1)+j+1]);

% Each team must have a driver:
constraint forall(i in 1..t)(shift[wpt*(i-1)+1]=teamDrivers[i]);

% Sum of all team members' individual satisfaction. Use this
% to calculate (average) satisfaction across the shift
array[1..t] of var int: teamsatisf;
constraint forall(i in 1..t)(teamsatisf[i] = sum([ P[shift[wpt*(i-1)+j], shift[wpt*(i-1)+k]] | j in 1..wpt, k in 1..wpt ]));

% No person works with the person he dislikes the most:
constraint forall(i in 1..t, j in 1..wpt, k in 1..wpt)(P[shift[wpt*(i-1)+j], shift[wpt*(i-1)+k]] < n-1);

% Objective:
solve minimize sum(teamsatisf);
output[ show(sum(teamsatisf)) ];
