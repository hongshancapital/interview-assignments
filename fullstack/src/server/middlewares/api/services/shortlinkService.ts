import { pool } from "../../../db.js";

const BASE62_CHARSET =
  "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");

export function Base62Encode(bigInt: bigint) {
  let result = "";
  while (bigInt > 0n) {
    result = BASE62_CHARSET[parseInt((bigInt % 62n).toString())] + result;
    bigInt = bigInt / 62n;
  }
  return result;
}

export function Base62Decode(base62: string) {
  let bigIntResult = 0n;
  const base62Lenth = base62.length;

  for (let i = 0; i < base62Lenth; i++) {
    const base62Char = base62.at(i);

    if (!base62Char) return undefined;

    const bigIntNUmber = BigInt(BASE62_CHARSET.indexOf(base62Char));

    bigIntResult += bigIntNUmber * 62n ** BigInt(base62Lenth - i - 1);
  }

  return bigIntResult;
}

/**
 * @param originLink original url, max lenth 65535 characters
 * @returns shortlink with auto increase base62 string
 */
export async function createShortLink(originLink: string) {
  const trimOriginLink = originLink.trim();

  return new Promise<string>((resolve, reject) => {
    pool.getConnection((err, connection) => {
      if (err) return reject(err);

      connection.query(
        `SELECT shortlinkId, originalLink FROM short_origin_table WHERE originalLink=${connection.escape(
          trimOriginLink
        )};`,
        (err, results) => {
          if (err) return reject(err);

          if (results.length === 0) {
            connection.beginTransaction((err) => {
              if (err) return reject(err);

              connection.query(
                `SELECT * FROM short_origin_table ORDER BY createTime DESC LIMIT 1;`,
                (err, result) => {
                  if (err) {
                    connection.rollback();
                    return reject(err);
                  }

                  // limit to maxium 8 characters
                  const latestShortlinkId = Base62Decode(result[0].shortlinkId);

                  if (!latestShortlinkId) return reject();

                  const shortlinkId = Base62Encode(
                    latestShortlinkId + 1n
                  ).substring(0, 8);

                  connection.query(
                    `INSERT INTO short_origin_table (shortlinkId,originalLink, createTime) VALUES ('${shortlinkId}','${trimOriginLink}', '${new Date()
                      .toISOString()
                      .replace("Z", "")}');`,
                    (err, result) => {
                      if (err) {
                        connection.rollback();
                        return reject(err);
                      }

                      connection.commit((err) => {
                        if (err) {
                          connection.rollback();
                          return reject(err);
                        }

                        return resolve(shortlinkId);
                      });
                    }
                  );
                }
              );
            });
          } else {
            return resolve(results[0].shortlinkId);
          }
        }
      );
    });
  });
}

/**
 * @param shortlinkId short link id, max lenth 8 characters
 * @returns origin link
 */
export async function getOriginLinkById(shortlinkId: string) {
  return new Promise<string>((resolve, reject) => {
    if (shortlinkId.length > 8) return reject(false);
    pool.getConnection((err, connection) => {
      if (err) return reject();

      connection.query(
        `SELECT originalLink FROM short_origin_table WHERE shortlinkId=${connection.escape(
          shortlinkId,
          true
        )};`,
        (err, results) => {
          if (err) return reject();

          if (results.length === 0) return reject();

          return resolve(results[0].originalLink);
        }
      );
    });
  });
}
