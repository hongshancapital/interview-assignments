//
//  RegisterView.swift
//  Login
//
//  Created by xiwang wang on 2021/8/31.
//

import SwiftUI

struct RegisterView: View {
    /// 输入表单规则校验
    // 用户名
    @ObservedObject private var validateName: ValidatedValue<String> = ValidatedValue(
        "",
        validator: { current, new in
            return new
        }
    )
    // 密码
    @ObservedObject private var validatePassword: ValidatedValue<String> = ValidatedValue(
        "",
        validator: { current, new in
            return new
        }
    )
    
    // 密码
    @ObservedObject private var validateRepeatPassword: ValidatedValue<String> = ValidatedValue(
        "",
        validator: { current, new in
            return new
        }
    )
    
    var body: some View {
        VStack(alignment: .center){
            VStack(alignment: .center){
                LoginTextFieldView(string: $validateName.value, type: .userName)
                LoginTextFieldView(string: $validatePassword.value, type: .password)
                LoginTextFieldView(string: $validateRepeatPassword.value, type: .password, placeholder: "Repeat Password")
                
                Button(action: {
                    
                }, label: {
                    Rectangle()
                        .fill(validateName.value.count > 0 && validatePassword.value.count > 8 && validateRepeatPassword.value == validatePassword.value ? Color("themeGreen"): Color("disableGray"))
                        .frame(height: 44, alignment: .center)
                        .overlay(Text("Create Acount").foregroundColor(.white).bold())
                        .cornerRadius(8)
                        .padding(EdgeInsets(top: 25, leading: 30, bottom: 0, trailing: 30))
                    
                })
                
                
            }
            
        }
    }
}

struct RegisterView_Previews: PreviewProvider {
    static var previews: some View {
        RegisterView()
    }
}
