import SwiftUI
import Foundation
import Combine

struct LoginAndCreateAccountView: View {
    
    @State private var name = ""
    @State private var password = ""
    @State private var repeatPassword = ""

    @State var showAlert = false
    @State private var isLoginView: Bool = true
    @State private var showCreateAccount: Bool = false

    @EnvironmentObject var userSettings: UserSettings

    @State var alertMsg: String = "error"
    var alert: Alert {
        Alert(title: Text(""), message: Text(alertMsg), dismissButton: .default(Text("OK")))
    }

    var services = Service()

    var body: some View {
        VStack {
            Spacer()
                .frame(width: Width, height: Heigh/5)
            InputTextField(inputText: $name, textFieldType: .name)
            InputTextField(inputText: $password, textFieldType: .password)

            if !isLoginView {
                InputTextField(inputText: $repeatPassword, textFieldType: .repeatPassword)
            } else {
                HStack {
                    Spacer()

                    Button("Create Account" , action: {
                        self.isLoginView = false
                        name = ""
                        password = ""
                        repeatPassword = ""
                    })
                    .foregroundColor(lightGreyColor)
                    .font(.system(size: (Width * 15) / 414, weight: .bold, design: .default))
                }
                .padding(.trailing, 20)
            }

            Spacer()
                .frame(width: Width, height: 50)

            if isLoginView {
                Button(action: {
                    if isValidInputs() {
                        let loginModel = LoginModel(
                            name: name,
                            password: password
                        )
                        self.services.userLogin(login: loginModel)
                            .sink { result in
                                if result {
                                    UserDefaults.standard.set(true, forKey: "loggedIn")
                                    UserDefaults.standard.synchronize()

                                    self.userSettings.loggedIn = true
                                } else {
                                    alertMsg = "Name Or Password is wrong."
                                    self.showAlert.toggle()
                                }
                            }
                            .cancel()
                    }
                }){
                    Text("Login")
                        .frame(width: Width -  50, height: 40, alignment: .center)
                        .foregroundColor(Color.white)
                        .background(inputToComplete() ? greenColor : lightGreyColor)
                        .font(.system(size: 18, weight: .bold, design: .default))
                }
                .cornerRadius(10)
                .alert(isPresented: $showAlert, content: { self.alert })
            } else {
                Button(action: {
                    if isValidInputs() {
                        let loginModel = LoginModel(
                            name: name,
                            password: password,
                            repeatPassword: repeatPassword
                        )
                        self.services.userRegistration(login: loginModel)
                            .sink { result in
                                if result {
                                    UserDefaults.standard.set(true, forKey: "loggedIn")
                                    UserDefaults.standard.synchronize()

                                    self.userSettings.loggedIn = true
                                } else {
                                    alertMsg = "Password is not valid."
                                    self.showAlert.toggle()
                                }
                            }
                            .cancel()
                    }
                }){
                    Text("Create Account")
                        .frame(width: Width -  50, height: 40, alignment: .center)
                        .foregroundColor(Color.white)
                        .background(createAccountInputToComplete() ? greenColor : lightGreyColor)
                        .font(.system(size: 18, weight: .bold, design: .default))
                }
                .cornerRadius(10)
                .alert(isPresented: $showAlert, content: { self.alert })
            }

            Spacer()
        }
        .animation(isLoginView ? .easeOut(duration: 1) : .easeOut(duration: 0.5))
    }

    /// Determine whether to fill in the information in LoginView
    func inputToComplete() -> Bool {
        if !name.isEmpty, !password.isEmpty{
            return true
        }
        return false
    }

    /// Determine whether to fill in the information in Register View
    func createAccountInputToComplete() -> Bool {
        if !name.isEmpty, !password.isEmpty, !repeatPassword.isEmpty {
            return true
        }
        return false
    }

    /// Determine whether the information entered is compliant and store the information at the same time
    fileprivate func isValidInputs() -> Bool {
        var isValid = false
        if isLoginView {
            if self.name == "" {
                alertMsg = "Name can't be blank."
                isValid = true
            } else if self.password == "" {
                alertMsg = "Password can't be blank."
                isValid = true
            }
        } else {
            if self.name == "" {
                alertMsg = "Name can't be blank."
                isValid = true
            } else if self.password == "" {
                self.alertMsg = "Password can't be blank."
                isValid = true
            } else if self.password != self.repeatPassword {
                self.alertMsg = "The two passwords do not match"
                isValid = true
            }
        }

        if isValid {
            self.showAlert.toggle()
            return false
        }
        return true
    }
}

struct LoginAndCreateAccountView_Previews: PreviewProvider {
    static var previews: some View {
        LoginAndCreateAccountView()
    }
}
