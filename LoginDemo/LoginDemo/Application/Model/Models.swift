import Foundation

struct Result {
    var msg = ""
    var code = -10000
    var data = false
}

struct AccountData: Codable {
    var username: String
    var password: String
}

struct UserCreateData: Codable {
    var accountData: [AccountData]
}

