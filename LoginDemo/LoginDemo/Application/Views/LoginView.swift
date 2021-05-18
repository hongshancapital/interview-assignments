import Combine
import SwiftUI

struct LoginView: View {
    @StateObject var viewModel = LoginViewModel()
    @Binding var isRoot: Bool
    @State var isAlert = false
    @State var msg: String = ""
    let primaryButton = Alert.Button.default(Text("Yes")) {}
    
    var body: some View {
        BackgroundView {
            ZStack {
                VStack(spacing:70) {
                    VStack(alignment: .trailing, spacing: 0) {
                        FormView(userName: $viewModel.userName, password: $viewModel.password, repeatPassword: $viewModel.repeatPassword, isRegistered: $viewModel.isRegistered)
                            .animation(Animation.spring().delay(0.2))
                        
                        Button(action: viewModel.switchLoginOrRegistered, label: {
                            Text(viewModel.isRegistered ? "Login" : "Create Account" )
                                .foregroundColor(Color(hex: 0xd7d7d7))
                                .animation(Animation.easeOut(duration: 0.4))
                        }).padding(.trailing, 15)
                    }
        
                    
                    Button(action: {
                        viewModel.dataRequest { result,msg in
                            if result {
                                UserDefaultsConfig.username = viewModel.userName
                                isRoot = true
                                isAlert = false
                            } else {
                                isAlert = true
                                self.msg = msg
                            }
                        }
                    }, label: {
                        HStack {
                            Text(viewModel.isRegistered ? "Create Account":"Login")
                                .bold()
                            Image(systemName: "arrow.right")
                                .font(.system(size: 14))
                        }
                    })
                    .frame(width: kScreenWidth - 30*2, height: 44, alignment: .center)
                    .background(viewModel.isClick ? Color.green : Color(hex: 0xd9d9d9))
                    .foregroundColor(.white)
                    .cornerRadius(15)
                    .allowsHitTesting(viewModel.isClick)
                    .alert(isPresented: $isAlert, content: {
                        Alert(title: Text("温馨提示"), message: Text(msg), dismissButton: primaryButton)
                    })
                }
                LoadingView(isAction: $viewModel.isLoading)
            }
        }.onTapGesture {
            endEditing()
        }
    }
}
struct FormView: View {
    @Binding var userName : String
    @Binding var password : String
    @Binding var repeatPassword : String
    @Binding var isRegistered : Bool
    
    var body: some View {
        VStack(alignment:.trailing) {
            HStack {
                TextField("Name", text: $userName)
            }
            
            Rectangle()
                .fill(Color.black.opacity(0.2))
                .frame(height: 1)
            
            HStack{
                SecureField("Password", text: $password)
            }.padding(.top, 20)
            
            Rectangle()
                .fill(Color.black.opacity(0.2))
                .frame(height: 1)
            
            if isRegistered {
                VStack(alignment: .leading) {
                    HStack{
                        SecureField("Repeat Password", text: $repeatPassword)
                    }.padding(.top, 20)
                    
                    Rectangle()
                        .fill(Color.black.opacity(0.2))
                        .frame(height: 1)
                }
            }
            
        }.padding()
    }
}
