appdir=/home/erik/Dropbox/Master_folder/School/MSc/ht16/m4co/MiniZinc/MiniZincIDE-2.0.14-bundle-linux-x86_64/
datadir=/home/erik/Dropbox/Master_folder/Misc_code/Java/Jobbapp/
subdir=solver/

fzn=flatzinc
app=mzn2fzn
model=solve.mzn
data=data.dzn

# Call:
echo $appdir$app $subdir$model $datadir$data
$appdir$app $subdir$model $datadir$data

# Now we have an fzn- and ozn-file. Call
#var=$($appdir$fzn "solve.fzn" | grep "x" | cut -d= -f 2)

#echo "var is $var"
