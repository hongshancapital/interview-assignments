//
//  ContentView.swift
//  HSLoginTest
//
//  Created by 伟龙 on 2021/2/25.
//

import SwiftUI
import Combine
@main
struct MainApp:App {
    var  body:some Scene {
        WindowGroup{
            UserComponentView()
        }
    }
}
//use  username lotawei password 12345678 will be passed
struct UserComponentView: View {
    @State var  saclefix:CGFloat = 0.8
    @ObservedObject  var  userViewModel:UserLoginViewModel = UserLoginViewModel.init()
    @ObservedObject  var  createViewModel:UserCreatViewModel =   UserCreatViewModel.init()
    @State   private var  state:LoginState = LoginState.login
    @State var loginedUser:UserModel = UserModel.init()
    var body: some View {
        switch state  {
        case .login:
            loginview
                .userScaleAnimation(saclefix)
                .onAppear(perform: {
                    self.saclefix = 1
                })
        case .regist:
            registview
                .userScaleAnimation(saclefix)
                .onAppear(perform: {
                    self.saclefix = 1
                })
        case .logined:
            loginedview
                .userScaleAnimation(saclefix)
                .onAppear(perform: {
                    self.saclefix = 1
                })
        }
    }
    func resetScaleFix() {
        self.saclefix = 0.8
    }
}
extension  UserComponentView{
    var loginview : some View {
        userViewModel.resultCallBack =
            { (apiresult) in
                switch apiresult {
                case .success(let  user):
                    guard let loginedusr = user as? UserModel else {
                        return
                    }
                    self.loginedUser = loginedusr
                    self.resetScaleFix()
                    self.state = .logined
                case .failed(let err):
                    debugPrint("--- error occurred\(err)")
                    break
                }
            }
        return   UserLoginView(userViewModel: userViewModel,registAction: {
            self.state = .regist
        })
    }
}
extension UserComponentView {
    var registview : some View {
        createViewModel.resultcallback =
            { (apiresult) in
                switch apiresult {
                case .success(let  user):
                    guard let loginedusr = user as? UserModel else {
                        return
                    }
                    self.loginedUser = loginedusr
                    self.resetScaleFix()
                    self.state = .logined
                case .failed(let err):
                    debugPrint("--- error occurred\(err)")
                    break
                }
            }
        return UserRegistView(createViewModel: createViewModel){
            self.resetScaleFix()
            self.state = .login
        }
    }
}
extension UserComponentView {
    var loginedview : some View {
        UserLoginedView(loginedUser:loginedUser){
            self.resetScaleFix()
            self.state = .login
        }
    }
}

/// user loginview
struct UserLoginView:View{
    @ObservedObject var userViewModel:UserLoginViewModel
    var registAction:(() -> Void)?
    var body: some View{
        ZStack {
            if  self.userViewModel.loading {
                ProgressView()
                    .progressViewStyle(CircularProgressViewStyle(tint: Color.green))
                    .frame(width: 200, height: 200)
            }
            else{
                ScrollView{
                    Spacer().frame(minHeight:200)
                    TextField("Name",text: $userViewModel.userName)
                        .padding(loginandresgiteredgeInset)
                    Divider().padding(loginandresgiteredgeInset)
                    
                    HStack{
                        Spacer().frame(width:20)
                        Text(userViewModel.userNameTipMessge)
                            .foregroundColor(.red)
                            .font(.system(size: 8))
                        Spacer()
                    }
                    
                    
                    SecureField("Passwrod", text:$userViewModel.passWord)
                        .padding(loginandresgiteredgeInset)
                    Divider().padding(loginandresgiteredgeInset)
                    HStack{
                        Spacer().frame(width:20)
                        Text(userViewModel.userPassWordTipMessage)
                            .foregroundColor(.red)
                            .font(.system(size: 8))
                        Spacer()
                    }
                    HStack{
                        Spacer()
                        Button(action:
                                {
                                    self.registAction?()
                                })
                        {
                            Text("Create Account")
                        }.padding()
                    }
                    Button(action:
                            {
                                userViewModel.checklogin()
                                
                            })
                    {
                        Text(userViewModel.displayText)
                            .frame(width:250, height: 50)
                            .foregroundColor(Color.white)
                            .background(userViewModel.isValid ? Color.green:Color.gray)
                            .cornerRadius(8)
                    }
                    .alert(isPresented: $userViewModel.alterMessage.0){ () -> Alert in
                        Alert(title:Text(self.$userViewModel.alterMessage.1.wrappedValue))
                    }
                    .disabled(!userViewModel.isValid)
                }.frame(maxHeight: .infinity)
                .ignoresSafeArea()
            }
        }
    }
}

///user  register  view
struct  UserRegistView:View {
    @ObservedObject  var  createViewModel:UserCreatViewModel
    var   cancleAction:(()->Void)?
    var body: some View{
        if  self.createViewModel.loading {
            ProgressView().progressViewStyle(CircularProgressViewStyle(tint: Color.green))
                .frame(width: 200, height: 200)
        }else{
            ScrollView{
                Spacer().frame(minHeight:200)
                TextField("Name",text: $createViewModel.registUserName)
                    .padding(loginandresgiteredgeInset)
                Divider().padding(loginandresgiteredgeInset)
                SecureField("Passwrod", text:$createViewModel.registPassword)
                    .padding(loginandresgiteredgeInset)
                Divider().padding(loginandresgiteredgeInset)
                SecureField("Confirm Passwrod", text:$createViewModel.passwordConfirm)
                    .padding(loginandresgiteredgeInset)
                Divider().padding(loginandresgiteredgeInset)
                HStack{
                    Spacer().frame(width:20)
                    Text(createViewModel.tipMessage)
                        .foregroundColor(.red)
                        .font(.system(size: 8))
                }.frame(height:20)
                Button(action:
                        {
                            createViewModel.checkregist()
                        })
                {
                    Text("Create Account")
                        .frame(width:250, height: 50)
                        .foregroundColor(Color.white)
                        .background(createViewModel.createUserValid ? Color.green:Color.gray)
                        .cornerRadius(8)
                }
                .alert(isPresented: $createViewModel.alterMessage.0){ () -> Alert in
                    Alert(title:Text(self.$createViewModel.alterMessage.1.wrappedValue))
                }
                Button(action:
                        {
                            self.cancleAction?()
                        })
                {
                    Text("Cancel")
                        .frame(width:250, height: 50)
                        .foregroundColor(.white)
                        .background(Color.gray)
                        .cornerRadius(8)
                }.padding()
            }.frame(maxHeight: .infinity)
            .ignoresSafeArea()
        }}
}

/// user login success
struct UserLoginedView:View {
    @State var  loginedUser:UserModel
    var  logoutAction:(()->Void)?
    var body: some View{
        VStack {
            ZStack {
                RoundCornerPath(radius: 16,corners: [.bottomLeft,.bottomRight])
                    .frame(height:250)
                    .foregroundColor(.green)
                VStack{
                    Image("default")
                        .resizable()
                        .frame(width: 90, height:90)
                        .cornerRadius(45)
                    Text($loginedUser.userName.wrappedValue)
                }
                Spacer()
            }
            Spacer()
            Button(action:
                    {
                        self.logoutAction?()
                    })
            {
                Text("Log out ")
                    .frame(width:250, height: 50)
                    .foregroundColor(.red)
                    .background(backgrayColor)
                    .cornerRadius(8)
            }.padding()
            Spacer().frame(height:80)
        }.ignoresSafeArea()
    }
}


struct InputcomponentsView_Previews: PreviewProvider {
    static var previews: some View {
        UserComponentView()
    }
}
