//
//  LoginView.swift
//  LoginDemo
//
//  Created by 吕博 on 2021/7/27.
//

import SwiftUI

struct LoginView: View {

    @StateObject var AccountVM = AccountViewModel(style: .Login)
    @State var username: String = ""
    @State var password: String = ""

    var body: some View {
        
        NavigationView {
            VStack(alignment: .center, spacing: 20.0) {
                
                /// User Name
                CustomTextField(type: .text, placeholder: "Name", text: $AccountVM.name)
                /// Password
                CustomTextField(type: .password, placeholder: "Password", text: $AccountVM.password)
     
                VStack {
                    /// Create Acccount Row
                    TipandCreateRowPart()
                    /// Login Row
                    SubmitButtonPart(style: .Login, user: username, password: password)
                }
                .environmentObject(AccountVM)
                
                Spacer()
            }
            .frame(width: UIScreen.main.bounds.width - 80)
            .padding(.top, 40)
            .navigationTitle("Login")
        }
    }
}

struct CreateAccountView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}

// Tips and "Create Account" Row
struct TipandCreateRowPart: View {
    @EnvironmentObject var AccountVM: AccountViewModel
    var body: some View {
        HStack {
            Text(AccountVM.tip)
                .font(.footnote)
                .foregroundColor(.red)
            Spacer()
            
            // use navigation mode of rebeloper from youtube
            NavigationStep(type: .push, style: .view) {
                SignupView()
            } label: {
                Text("Create Account")
                    .foregroundColor(.gray)
            }
        }
    }
}

