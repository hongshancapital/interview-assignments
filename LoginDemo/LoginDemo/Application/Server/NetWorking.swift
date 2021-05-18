import Foundation
import Combine

fileprivate var savePath: URL!
fileprivate let encoder = JSONEncoder()
fileprivate let decoder = JSONDecoder()

protocol NetWorkingService {
    
    /// Login request
    /// - Parameter params: Request parameters
    func accountLogin(params: AccountData) -> AnyPublisher<Result, Never>
    
    /// Account registration
    /// - Parameter params: Request parameters
    func accountRegistration(params: AccountData) ->  AnyPublisher<Result, Never>
}

struct LoginService: NetWorkingService {

    var accountData: UserCreateData
    
    init() {
        do {
            let icloudDirectory = FileManager.default.url(forUbiquityContainerIdentifier: nil)
            let documentDirectory = try FileManager.default.url(for: .documentDirectory,
                                                                in: .userDomainMask,
                                                                appropriateFor: nil,
                                                                create: false)
            if let icloudDirectory = icloudDirectory {
                try FileManager.default.startDownloadingUbiquitousItem(at: icloudDirectory)
            }
            savePath = (icloudDirectory ?? documentDirectory).appendingPathComponent("userData")
        } catch let error {
            fatalError("Couldn't create save state data with error: \(error)")
        }
        
        if let data = try? Data(contentsOf: savePath),
            let savedData = try? decoder.decode(UserCreateData.self, from: data) {
            self.accountData = savedData
        } else {
            self.accountData = UserCreateData(accountData: [])
        }

    }
    
    func accountLogin(params: AccountData) -> AnyPublisher<Result, Never> {
        let username = params.username
        let usernames = accountData.accountData.filter { $0.username == username }
        guard !usernames.isEmpty else {
            let result = Result(msg: "The account does not exist", code: 0, data: false)
            return Just(result).eraseToAnyPublisher()
        }
        
        guard usernames.first!.password == params.password else {
            let result = Result(msg: "Account and password do not match", code: 0, data: false)
            return Just(result).eraseToAnyPublisher()
        }
        
        let result = Result(msg: "Login successfull", code: 200, data: true)
        return Just(result).eraseToAnyPublisher()
    }
    
    func accountRegistration(params: AccountData) -> AnyPublisher<Result, Never> {
        let username = params.username
        let usernames = accountData.accountData.filter { $0.username == username }
        guard usernames.isEmpty else {
            let result = Result(msg: "Registration failed, account already exists", code: 0, data: false)
            return Just(result).eraseToAnyPublisher()
        }
        var savingState = self
        savingState.accountData.accountData.append(params)
        saveAccount(accountData: savingState.accountData)
        
        let result = Result(msg: "Account registration successful", code: 200, data: true)
        return Just(result).eraseToAnyPublisher()
        
    }
    
    func saveAccount(accountData: UserCreateData) {
        guard let data = try? encoder.encode(accountData) else {
            return
        }
        do {
            try data.write(to: savePath)
        } catch let error {
            print("Error while saving app state :\(error)")
        }
    }
}
