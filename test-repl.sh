#!/bin/bash
# Comprehensive REPL Test Script
# Tests various features of the Group Theory REPL

cd "$(dirname "$0")"

echo "Building project..."
mvn compile -q

if [ $? -ne 0 ]; then
    echo "Build failed!"
    exit 1
fi

echo ""
echo "========================================"
echo "  Group Theory REPL - Feature Tests"
echo "========================================"
echo ""

# Test 1: Basic group creation and properties
echo "TEST 1: Basic Group Creation"
echo "----------------------------"
cat << 'EOF' | java -cp target/classes d021248.group.repl.GroupREPL
g = Z(6)
g.order
g.identity
quit
EOF

echo ""
echo "TEST 2: Group Property Functions"
echo "--------------------------------"
cat << 'EOF' | java -cp target/classes d021248.group.repl.GroupREPL
g = Z(6)
isAbelian(g)
isCyclic(g)
order(g)
quit
EOF

echo ""
echo "TEST 3: Multiple Groups"
echo "----------------------"
cat << 'EOF' | java -cp target/classes d021248.group.repl.GroupREPL
g1 = Z(4)
g2 = D(3)
g3 = S(3)
groups
quit
EOF

echo ""
echo "TEST 4: Dihedral Group Analysis"
echo "-------------------------------"
cat << 'EOF' | java -cp target/classes d021248.group.repl.GroupREPL
h = D(4)
order(h)
isAbelian(h)
isCyclic(h)
quit
EOF

echo ""
echo "TEST 5: Subgroups and Center"
echo "----------------------------"
cat << 'EOF' | java -cp target/classes d021248.group.repl.GroupREPL
g = Z(6)
subgroups(g)
center(g)
quit
EOF

echo ""
echo "TEST 6: Symmetric Group"
echo "----------------------"
cat << 'EOF' | java -cp target/classes d021248.group.repl.GroupREPL
s = S(4)
order(s)
isAbelian(s)
quit
EOF

echo ""
echo "TEST 7: Alternating Group"
echo "------------------------"
cat << 'EOF' | java -cp target/classes d021248.group.repl.GroupREPL
a = A(4)
order(a)
isAbelian(a)
quit
EOF

echo ""
echo "TEST 8: Variables and State"
echo "--------------------------"
cat << 'EOF' | java -cp target/classes d021248.group.repl.GroupREPL
x = 5
y = 10
z = Z(6)
vars
groups
quit
EOF

echo ""
echo "TEST 9: Numeric Operations"
echo "-------------------------"
cat << 'EOF' | java -cp target/classes d021248.group.repl.GroupREPL
2 + 3
5 * 4
2 ^ 8
10 % 3
quit
EOF

echo ""
echo "TEST 10: Help System"
echo "-------------------"
cat << 'EOF' | java -cp target/classes d021248.group.repl.GroupREPL
help()
quit
EOF

echo ""
echo "========================================"
echo "  All Tests Completed!"
echo "========================================"
