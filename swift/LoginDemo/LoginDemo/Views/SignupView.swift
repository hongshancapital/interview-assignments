//
//  SignupView.swift
//  LoginDemo
//
//  Created by 吕博 on 2021/7/28.
//

import SwiftUI

struct SignupView: View {

    @StateObject var AccountVM = AccountViewModel(style: .Signup)
    @State var username: String = ""
    @State var password: String = ""
    
    var body: some View {
        
        
        VStack(alignment: .center, spacing: 20.0) {
            
            /// User Name
            CustomTextField(type: .text, placeholder: "Name", text: $AccountVM.name)
            /// Password
            CustomTextField(type: .password, placeholder: "Password", text: $AccountVM.password)
            /// Repeat Password
            CustomTextField(type: .password, placeholder: "Repeat Password", text: $AccountVM.repeatpwd)
            
            /// Tips Row
            TipsRow()
            /// Login Row
            SubmitButtonPart(style: .Signup, user: username, password: password)
            
            Spacer()
        }
        .environmentObject(AccountVM)
        .frame(width: UIScreen.main.bounds.width - 80)
        .padding(.top, 40)
        .navigationTitle("Sign up")
    }

}

struct SignupView_Previews: PreviewProvider {
    static var previews: some View {
        SignupView()
    }
}

// Tips Row
struct TipsRow: View {
    @EnvironmentObject var AccountVM: AccountViewModel
    var body: some View {
        HStack {
            Text(AccountVM.tip)
                .font(.footnote)
                .foregroundColor(.red)
            Spacer()
        }
    }
}
