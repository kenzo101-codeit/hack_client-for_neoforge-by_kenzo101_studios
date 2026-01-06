#!/usr/bin/env bash
set -e

echo "=============================="
echo " Building NeoForge Mod JAR"
echo "=============================="

./gradlew :neoforge:clean
./gradlew :neoforge:remapJar

echo
echo "=============================="
echo " NeoForge JAR built!"
echo "=============================="

mv neoforge/build/libs/neoforge-1.1.1.jar neoforge/build/libs/HackClient-1.21.1.jar
ls neoforge/build/libs