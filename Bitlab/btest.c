/*
 * btest.c
 * Test shell for bitlab
 * William Chargin
 * 7 January 2014 
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "bits.h"
#include "tester.h"

bool testUnaryOperation(int count, bool (*test)(type)) {
    /* Test special cases with test({-1, 0, 1}) */
    if (!test(0))  return false;
    if (!test(1))  return false;
    if (!test(-1)) return false;
    
    /* Test random values */
    for (count--; count >= 0; count--) {
        if (!test(rand())) {
            return false;
        }
    }
    return true;
}

bool testBinaryOperation(int count, bool (*test)(type, type)) {
    /* Test special cases with test({-1, 0, 1}, {-1, 0, 1}) */
    int i, j;
    for (i = -1; i <= 1; i++) {
        for (j = -1; j <= 1; j++) {
            if (!(test(i, j))) {
                return false;
            }
        }
    }
    
    /* Test random values */
    for (count--; count >= 0; count--) {
        if (!test(rand(), rand())) {
            return false;
        }
    }
    return true;
}

bool testBitAnd(type x, type y);
bool testBitOr(type x, type y);
bool testIsEqual(type x, type y);
bool testLogicalShift(type x, type n);

bool testBitParity(type x);
bool testLeastBitPos(type x);

int main() {
    test t;
    
    /* Initialize random number generator */
    srand(time(NULL));
    
    t = t_new("Bitlab Shell");
    
    printf("Testing bitAnd... ");
    _(t, testBinaryOperation(10000, &testBitAnd), "passes.");
    
    printf("Testing bitOr... ");
    _(t, testBinaryOperation(10000, &testBitOr), "passes.");
    
    printf("Testing isEqual... ");
    _(t, testBinaryOperation(10000, &testIsEqual), "passes.");
    
    printf("Testing logicalShift... ");
    _(t, testBinaryOperation(10000, &testLogicalShift), "passes.");
    
    printf("Testing bitParity... ");
    _(t, testUnaryOperation(10000, &testBitParity), "passes.");
    
    printf("Testing leastBitPos... ");
    _(t, testUnaryOperation(10000, &testLeastBitPos), "passes.");
    
    t_done();
    
    return 0;
}

bool testBitAnd(type x, type y) { return (x & y) == bitAnd(x, y); }
bool testBitOr(type x, type y) { return (x | y) == bitOr (x, y); }
bool testIsEqual(type x, type y) { return (x == y) == isEqual(x, y); }
bool testLogicalShift(type x, type n) {
    n = n % 31;
    return (x >> n) == logicalShift(x, n);
}
bool testBitParity(type x) {
    type parity = 0;
    type t = x;
    while (t > 0) {
       parity += t & 0x1;
       t >>= 1;
    }
    parity %= 2;
    return parity == bitParity(x);
}
bool testLeastBitPos(type x) {
    int pos;
    type test = leastBitPos(x);
    if (x == 0) {
        return test == 0;
    }
    pos = 0;
    while ((x & 0x1) == 0) {
        x >>= 1;
        pos++;
    }
    return test == (1U << pos);
}
