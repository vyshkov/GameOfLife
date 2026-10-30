#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")"

MAIN_CLASS="tk.vovanok.gameoflife.gui.MainWindow"
OUT_DIR="target/classes"

if command -v mvn >/dev/null 2>&1; then
    mvn -q compile
else
    # No third-party dependencies, so plain javac is enough when Maven is absent.
    rm -rf "$OUT_DIR"
    mkdir -p "$OUT_DIR"
    javac --release 25 -encoding UTF-8 -d "$OUT_DIR" $(find src/main/java -name '*.java')
fi

exec java -cp "$OUT_DIR" "$MAIN_CLASS"
