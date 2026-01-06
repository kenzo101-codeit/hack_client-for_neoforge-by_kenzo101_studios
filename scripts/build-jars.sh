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

ls neoforge/build/libs