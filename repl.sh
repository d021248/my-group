#!/bin/bash
# Group Theory REPL Launcher
# Interactive Computer Algebra System for exploring finite groups

cd "$(dirname "$0")"

# Compile if needed
if [ ! -d "target/classes" ] || [ "src/main/java" -nt "target/classes" ]; then
    echo "Compiling..."
    mvn compile -q
fi

# Launch REPL
java -cp target/classes d021248.group.repl.GroupREPL "$@"
