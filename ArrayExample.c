#include <stdio.h>

int main() {

    int a[] = {1,2,3,4,5,6,7,8,9};

    printf("Hello %d", a[1]);
    a[1] = 5;
    printf("Hello %d ", a[1]);
    return 0;
}