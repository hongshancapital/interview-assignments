//
//  TextFieldView.swift
//  Login
//
//  Created by xiwang wang on 2021/8/30.
//

import SwiftUI

enum LoginTextFieldType {
    case userName, password
}

struct LoginTextFieldView: View {
    @State var string: Binding<String>
    var type: LoginTextFieldType = .userName
    var placeholder: String?
    var onEditingChanged: (Bool) -> Void = {b in }
    var onCommit: () -> Void = {}
    
    var body: some View {
        VStack{
            switch type {
            case LoginTextFieldType.userName:
                TextField(self.placeholder ?? "Name", text: string){ (b) in
//                    print("bbbb: ", b)
//                    print("bbbb name: ", string)
                    onEditingChanged(b)
                } onCommit: {
//                    print("onCommit name: ", string)
                    onCommit()
                }
                    .padding(EdgeInsets(top: 10, leading: 25, bottom: 10, trailing: 25))
            case LoginTextFieldType.password:
                SecureField(self.placeholder ?? "Password", text: string){
                    /// onCommit
                    onCommit()
                }
                    .padding(EdgeInsets(top: 10, leading: 25, bottom: 10, trailing: 25))
            }
            Rectangle()
                .fill(Color.gray)
                .frame(height: 0.5)
                .padding(EdgeInsets(top: 0, leading: 25, bottom: 0, trailing: 25))
        }
        
    }
}

struct TextFieldView_Previews: PreviewProvider {
    @State static var string = ""
    static var previews: some View {
        LoginTextFieldView(string: $string)
    }
}
