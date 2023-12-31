# GIT USAGE

## pull :
```
git fetch
git rebase
```
## commit : 
```
git status
git add <fichiers>
git commit -m "[PROJECT-SECTION] <message>" #On écrit en anglais en commançant par un verbe à l'infinitif (Remove file, Add functionnality ....)
```

Modification des précédents commit : 
```
git rebase -i HEAD~2 #Pour modifier le 2ème plus récent commit
git rebase --continue #Continuer à modifier les commit suivants (donc ici le premier plus récent)
git rebase --abort #Fin de la modif
git cherry-pick <commitnumber ex: 126acbe567c40eeb0daa0ce6a448076dae4fd617> #On se place sur le commit d'id 126acbe...
git commit --amend #modifie le plus récent comit sur lequel on est placé
git cherry-pick --continue

```
Pour plus d'options : https://git-scm.com/docs/git-commit 

## branches : 
```
git branch --list

```
Delete branch :
```
git branch -d <branch>
git branch -D <branch> #Pour forcer le delete 
```

## gitk :

Outil de visualisation de l'architecture git du projet. 
```
sudo apt install gitk
gitk
gitk --all
```