#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT_DIR"

echo "Cleaning build cache..."
./gradlew clean

echo "Building NeoForge artifact..."
./gradlew :neoforge:build --no-daemon

JAR=$(ls -1 neoforge/build/libs/hack_client_on_neoforge-neoforge-*.jar 2>/dev/null | grep -vE '(-sources|-dev-shadow)' | tail -n 1)
if [[ -z "$JAR" ]]; then
  echo "ERROR: Could not find built jar in neoforge/build/libs/"
  exit 1
fi

mkdir -p dist
cp "$JAR" dist/

echo "Packaged: dist/$(basename "$JAR")"
ls -l dist/ | grep $(basename "$JAR") || true
