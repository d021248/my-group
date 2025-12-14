#!/bin/bash
# Quick launcher script for group visualizations

if [ $# -eq 0 ]; then
    echo "Group Visualization Launcher"
    echo "Usage: ./viz.sh <group_type> <parameter>"
    echo ""
    echo "Group Types:"
    echo "  Z  or cyclic      - Cyclic group Z_n"
    echo "  D  or dihedral    - Dihedral group D_n"
    echo "  S  or symmetric   - Symmetric group S_n"
    echo "  A  or alternating - Alternating group A_n"
    echo ""
    echo "Examples:"
    echo "  ./viz.sh S 4      # Visualize S_4"
    echo "  ./viz.sh D 5      # Visualize D_5"
    echo "  ./viz.sh Z 12     # Visualize Z_12"
    echo ""
    echo "Or run the interactive demo launcher:"
    echo "  ./viz.sh demo     # Console menu"
    echo "  ./viz.sh gui      # GUI launcher"
    exit 0
fi

# Build if needed
if [ ! -d "target/classes" ]; then
    echo "Building project..."
    mvn compile -q
fi

# Check if demo mode
if [ "$1" = "demo" ]; then
    echo "Launching interactive demo launcher..."
    java -cp target/classes d021248.group.GroupDemo
    exit 0
fi

# Check if launcher mode
if [ "$1" = "launcher" ] || [ "$1" = "gui" ]; then
    echo "Launching GUI..."
    java -cp target/classes d021248.group.GroupDemo --gui
    exit 0
fi

# Run with arguments (using unified GroupDemo)
GROUP_TYPE="${1:-S}"
PARAM="${2:-4}"

echo "Launching visualizations for ${GROUP_TYPE}_${PARAM}..."
java -cp target/classes d021248.group.GroupDemo viz "$GROUP_TYPE" "$PARAM"

