import SwiftUI
import Combine

struct LoginView: View {
    @Binding var isPresented: Bool
    @Binding var userName: String
    @State var submitButtonTitle = LocalizedString.Login.login
    @State var modelButtonTitle = LocalizedString.Login.createAccount
    @State var alertPush = false
    @State var alertMessage: String = ""
    @State var passWord: String = ""
    @State var repeatPassWord: String = ""
    @State var isSelectLogIn = true

    let service = Service()

    var isPassWord: Bool {
        passWord == repeatPassWord && passWord.count > 8
    }

    var submitDisabled: Bool {
        guard isSelectLogIn else {
            return !userName.isEmpty && isPassWord ? false : true
        }
        return !userName.isEmpty && passWord.count > 8 ? false : true
    }

    var submitColor: Color {
        submitDisabled ? .gray : .darkGreen
    }
    var body: some View {
        VStack {
            VStack(alignment: .trailing, content: {
                DividerTextField(textField: AnyView(
                    TextField(LocalizedString.Login.userName, text: $userName)
                ))
                DividerTextField(textField: AnyView(
                    SecureField(LocalizedString.Login.passWord, text: $passWord)
                ))
                if !isSelectLogIn {
                    DividerTextField(textField: AnyView(
                        SecureField(LocalizedString.Login.RepeatPassWord, text: $repeatPassWord)
                    ))
                }
                Button(modelButtonTitle) { 
                    isSelectLogIn = !isSelectLogIn
                    repeatPassWord = ""
                    modelButtonTitle = isSelectLogIn ? LocalizedString.Login.createAccount : LocalizedString.Login.login
                    submitButtonTitle = isSelectLogIn ? LocalizedString.Login.login : LocalizedString.Login.createAccount                
                }
                .foregroundColor(.gray)
                Spacer().frame(height: 30)
            }) 
            Button(submitButtonTitle) {
                if isSelectLogIn {
                    loginAction()
                } else {
                    createAccountAction()
                }
            }
            .disabled(submitDisabled)
            .frame(width: UIScreen.main.bounds.width - 32*2, height: .buttonHeight, alignment: .center)
            .foregroundColor(.white)
            .background(submitColor)
            .cornerRadius(10)
            .alert(isPresented: $alertPush, content: {
                Alert(
                    title: Text("error"),
                    message: Text(alertMessage),
                    dismissButton: Alert.Button.cancel(Text("ok"))
                )
            })
        } 
        .padding(.bottom, 100)
        .animation(.easeInOut)
    }
    
}
extension LoginView {
    func loginAction() {
        let model = UserModel(userName: userName, passWord: passWord)        
        service.loginAccount(login: model).subscribe(Subscribers.Sink(
            receiveCompletion: { _ in },
            receiveValue: ({result in
                if result {
                    dismissAction()
                } else {
                    alertMessage = LocalizedString.Login.loginError
                    alertPush = true
                }
            })
        ))
    }

    func createAccountAction() {
        guard isPassWord else {  
            alertMessage = LocalizedString.Login.passWordError
            alertPush = true
            return 
        }
        let model = UserModel(userName: userName, passWord: passWord)        
        service.createAccount(login: model).subscribe(Subscribers.Sink(
            receiveCompletion: { _ in },
            receiveValue: ({ result in
                if result {
                    dismissAction()
                } else {
                    alertMessage = LocalizedString.Login.createAccountError
                    alertPush = true
                }
            })
        ))
    }

     func dismissAction() {
        isPresented = false
    }
}

struct DividerTextField: View {
    @State var textField: AnyView
    var body: some View {
        VStack{
            textField
            Divider()
        }
        .frame(width: UIScreen.main.bounds.width - 32*2, height: 60)
    }
}
