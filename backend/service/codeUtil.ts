const characters = '9gs0knlRaW8jCdmxKGNXf5DYq1vM4FzuQbtiZS2eUTEwr67BLyIHPc3VOphAJo';
const base = characters.length;

export function generateShortCode(id: number): string {
    let idOrigin = id;
    let shortCode = '';
    while (id > 0) {
        shortCode = characters[id % base] + shortCode;
        id = Math.floor(id / base);
    }
    const checksum = (idOrigin + 1) % base;
    const checksumBase62 = characters[checksum];
    return `${shortCode}${checksumBase62}`;
}

export function validateShortCodeAndGetIdIfTrue(shortCode: string): { id: number | undefined, isValid: boolean } {
    const checksum = shortCode.charAt(shortCode.length - 1);
    const withoutChecksum = shortCode.slice(0, -1);
    let id = 0;
    for (let i = 0; i < withoutChecksum.length; i++) {
        const char = withoutChecksum.charAt(i);
        const charIndex = characters.indexOf(char);
        if (charIndex < 0 || charIndex >= base) {
            return {
                isValid: false,
                id: undefined,
            };
        }
        id = id * base + charIndex;
    }
    let isValid = characters[(id + 1) % base] == checksum;
    return {
        isValid, 
        id: isValid ? id : undefined,
    };
}