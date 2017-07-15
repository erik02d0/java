% Solver for project "Jobbapp"

include "globals.mzn";

int: n; % number of employees
int: wpt = 3; % workers per team -- hard-coded for now
int: d; % number of drivers
array[1..n,1..n] of int: P; % preference matrix
int: t = n div wpt; % number of teams

% The array S is the solution
array[1..n] of var 1..n: S;
constraint alldifferent(S);

% Choose driver for each team:
array[1..t] of var 1..d: teamDrivers;
constraint alldifferent(teamDrivers);

% Symmetry-breaking:
constraint forall(i in 2..t)(teamDrivers[i-1] < teamDrivers[i]);
constraint forall(i in 1..t, j in 2..wpt-1)(S[wpt*(i-1)+j] < S[wpt*(i-1)+j+1]);

% Each team must have a driver:
constraint forall(i in 1..t)(S[wpt*(i-1)+1] = teamDrivers[i]);

% No person works with the person he dislikes the most:
constraint forall(i in 1..t, j in 1..wpt, k in 1..wpt)(P[S[wpt*(i-1)+j], S[wpt*(i-1)+k]] < n-1);

% Faux:
%var int: x;
%constraint (x = P[1,1]+P[n,n]);

solve satisfy;
output[ "%S = "++show(S) ]
