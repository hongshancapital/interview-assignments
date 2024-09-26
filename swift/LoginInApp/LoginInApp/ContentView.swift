//
//  ContentView.swift
//  LoginInApp
//
//  Created by yaojinhai on 2021/8/9.
//

import SwiftUI
import UIKit

struct ContentView: View {
    
    @State var isLoginApp = false
    @State var isShowRootView = false
    
    @State var isShowLogoutAlert = false
    
    
    var body: some View {
        mainView.background(Color(UIColor.systemBackground))
    }
    
    var mainView: some View {
        
        NavigationView(content: {
            userInfoView.navigationBarTitle(Text("home".localizedValue), displayMode: .inline).alert(isPresented: $isShowLogoutAlert) { () -> Alert in
                Alert(title: Text("tip".localizedValue), message: Text("configlogoutmessage".localizedValue), primaryButton: .cancel(), secondaryButton: .default(Text("ok".localizedValue), action: { 
                    UserInfoModel.logout()
                    isLoginApp = false
                }))
            }
        })
  
    }
    var userInfoView: some View {
        VStack(content: {
            Image(isLoginApp ? "userName" : "avater").clipShape(Circle(), style: FillStyle()).padding(.top, 20)
            Text(UserInfoModel.readCurrentUserModel()?.userName ?? "游客").font(Font.system(size: 14)).foregroundColor(Color(UIColor.label)).padding(10)


            if isLoginApp {
                
                Button("logout".localizedValue) { 
                    isShowLogoutAlert.toggle()
                }
            }else {
                
                NavigationLink("login".localizedValue, destination: LoginAppView(isShowRootView: $isShowRootView,loginSuccess: $isLoginApp), isActive: $isShowRootView)
            }

            Spacer()
        })
        
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

struct ActionButton: View {
    let buttonTitle: String
    @Binding var disabled: Bool
    var buttonAction: (() -> Void)?
    
    //    @Environment(\.colorScheme) var colorScheme
    
    
    var body: some View {
        Button(buttonTitle) { 
            buttonAction?()
        }.frame(width: UIScreen.main.bounds.width - 80,height: 40, alignment: .center).disabled(disabled).background(disabled ? Color.disableBackColor : Color.normalBackColor).cornerRadius(8).foregroundColor(disabled ? Color.disableTextColor : Color.normalTextColor)
    }
    
}


