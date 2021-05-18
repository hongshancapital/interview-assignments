import Foundation
import Combine

class UserCache {
    
    private var users: [UserModel] = []

    init() {
        let decoder = JSONDecoder()
        let defaults = UserDefaults.standard
        guard let saved = defaults.object(forKey: "user") as? Data else { return }
        guard let saveUsers = try? decoder.decode([UserModel].self, from: saved) else { return }
        users = saveUsers
    }
    
    func save() {
        let encoder = JSONEncoder()
        if let coded = try? encoder.encode(users) {
            let defaults = UserDefaults.standard
            defaults.setValue(coded, forKey: "user")
        }
    }

    func addUser(_ user: UserModel) {
        users.append(user)
        save()
    }
    
    func allUsers() -> [UserModel] {
        users
    }
    
    func searchUser(_ userName: String) -> UserModel? {
        let searchArray = users.filter{ $0.userName == userName }
        return searchArray.first
    }
}
