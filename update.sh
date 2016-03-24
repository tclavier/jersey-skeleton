#!/bin/bash

function display_banner (){
  echo "---------------------------------------"
  echo "Git update -  Projet agile Ludicode"
  echo "---------------------------------------"
  echo ""
}

function menu (){
  echo "[1] Mettre a jour les fichiers (action->pull)"
  echo "[2] Envoyer des fichiers (action->push)"
  echo "[3] Remplacer les changements locaux avec le dernier commit en ligne"
  echo "[4] Maven -> jetty:run (Lancer le serveur)"
  echo "[5] Maven -> compile (Compiler le projet)"
  echo "[6] Ajouter un tag (Git)"
  echo "[7] Supprimer un tag (Git)"
  echo ""
  read -p ">> Choisissez une action : "  mact
  echo ""
  if [ -z "$mact" ] || ((mact<=0 || mact>7))
    then
    clear
    display_banner
    menu
  fi
}

#Functions
function commit_name (){
  echo ""
  read -p ">> Entrer le nom du commit : " cmmtname
  if [ -z "$cmmtname" ]
    then
    echo ""
    echo "[-] Nom de commit invalide !"
    commit_name
  fi
}

function tag_prompt (){
  echo ""
  read -p ">> Entrer le nom du tag : " gittag
  if [ -z "$gittag" ]
    then
    echo "[-] Nom du tag invalide !"
    tag_prompt
  fi
}

#Main
clear
display_banner
menu
if [ "$mact" -eq "1" ]
  then
  echo "[*] Telechargement des derniers fichiers ..."
  git pull > /dev/null
  echo "[+] Telechargement termine"
elif [ "$mact" -eq "2" ]
  then
  echo "[*] Telechargement des derniers fichiers ..."
  git pull > /dev/null
  echo "[+] Telechargement termine"
  commit_name
  echo ""
  echo "[*] Envoie des modifications ..."
  git add ./ > /dev/null
  git commit -m "$cmmtname" > /dev/null
  git push -u origin master > /dev/null
  echo "[+] Envoie termine"
elif [ "$mact" -eq "3" ]
  then
  echo "[*] Ecrasement des changements locaux avec le dernier commit en ligne ..."
  git fetch origin
  git reset --hard origin/master
  echo "[+] Ecrasement termine"
elif [ "$mact" -eq "4" ]
  then
  mvn jetty:run
elif [ "$mact" -eq "5" ]
  then
  mvn compile
elif [ "$mact" -eq "6" ]
  then
  tag_prompt
  echo ""
  echo "[*] Ajout du tag ..."
  git tag "$gittag" > /dev/null
  git push --tags > /dev/null
  echo "[+] Ajout termine"
elif [ "$mact" -eq "7" ]
  then
  tag_prompt
  echo ""
  echo "[*] Suppression du tag ..."
  git tag -d "$gittag" > /dev/null
  git push origin :refs/tags/"$gittag"
  echo "[+] Tag supprime"
fi
echo ""
exit 0

