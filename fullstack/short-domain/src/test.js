function generateMatrix(n) {
    let startX = 0;
    let startY = 0;
    let loop = Math.floor(n/2); // 旋转圈数
    let current = 1; // 当前的数值
    // 创建二维数组
    let matrix = new Array(n);
    for (let i = 0; i < n; i++) {
        matrix[i] = new Array(n);
    }
    let length = n;

    // 进行四等分旋转 以3为例，可以分为 1->2 3->4 5->6 7->8 四组
    // 每组均为左闭右开
    while(loop--) {
        // 旋转的起始位置
        let x = startX;
        let y = startY;

        // 当前旋转的圈长



        // 第一组 n为3时，为1->2
        for (;x < length - 1; x++) {
            matrix[y][x] = current++;
        }


        // 第二组 n为3时，为3->4
        for (;y < length - 1; y++) {
            matrix[y][x] = current++;
        }

        //
        //
        // 第三组 n为3时，为5->6
        for (;x > startX; x--) {
            matrix[y][x] = current++;
        }

        //
        // // 第四组 n为3时，为7->8
        for (;y > startY; y--) {
            matrix[y][x] = current++;
        }

        //
        startX++;
        startY++;

        length = length -2; // 每次旋转一圈，边长减2
    }

    if (n % 2 !== 0) {
        // 奇数圈数中间位为最终值，直接填充即可
        const mid = Math.floor(n/2);
        matrix[mid][mid] = current;
    }

    return matrix;
}

generateMatrix(3);
