import Combine
import SwiftUI

class LoginViewModel: ObservableObject {
    /// The user name
    @Published var userName : String = ""
    /// password
    @Published var password : String = ""
    /// Verify password
    @Published var repeatPassword : String = ""
    /// Is it registered
    @Published var isRegistered : Bool = false
    /// can I click
    @Published var isClick: Bool = false
    
    @Published var isLoading = false
    var cancellable:Cancellable! {
        didSet {
            oldValue?.cancel()
        }
    }
    
    var loginServer = LoginService()
    
    
    deinit {
        cancellable.cancel()
    }
    
    init() {
        cancellable = self.$userName
            .combineLatest(self.$password,self.$repeatPassword,self.$isRegistered)
            { (username, password, repeatPassword, isRegistered) -> Bool in
                if isRegistered {
                    return username.count > 3 && password.count > 4 && repeatPassword == password
                }
                return username.count > 3 && password.count > 4
            }
            .sink { self.isClick = $0 }
    }
    
    // Switch login or registration
    func switchLoginOrRegistered() {
        isRegistered.toggle()
        delay(0.5) {
            if self.isRegistered {
                self.repeatPassword = ""
            }
        }
    }
    
    fileprivate func loginRequest(_ dispatch: @escaping (Bool,String) -> Void) {
        let accountData = AccountData(username: userName, password: password)
        _ = loginServer.accountLogin(params: accountData).sink { result in
            dispatch(result.data,result.msg)
        }
    }
    
    fileprivate func registerdRequest(_ dispatch: @escaping (Bool,String) -> Void) {
        let accountData = AccountData(username: userName, password: password)
        _ = loginServer.accountRegistration(params: accountData).sink(receiveValue: { result in
            dispatch(result.data,result.msg)
        })
    }
    
    func dataRequest(dispatch: @escaping (Bool,String) -> Void) {
        self.isLoading = true
        if isRegistered {
            delay(2) {
                self.isLoading = false
                self.registerdRequest(dispatch)
            }
            
        } else {
            delay(2) {
                self.isLoading = false
                self.loginRequest(dispatch)
            }
        }
    }
}
