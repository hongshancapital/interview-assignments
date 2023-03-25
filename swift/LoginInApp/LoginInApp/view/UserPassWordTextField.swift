//
//  UserPassWordTextField.swift
//  LoginInApp
//
//  Created by yaojinhai on 2021/8/9.
//

import Foundation
import SwiftUI

struct UserPassWordTextField: View {
    
    @Binding var passWord: String
    var placeText: String = "password".localizedValue
    
    var changeTextValue: ((_ value: String) -> Void)?
    
    @State var isShowPass = false
    
    @Environment(\.colorScheme) var colorScheme
    
    var body: some View {
        let binding = Binding<String>(get: {
            passWord
        }, set: {
            passWord = $0
            changeTextValue?($0)
        }) 
        
        
        return HStack {
//            CustomTextField(placeholder: placeText, text: $passWord, isSecureText: $isShowPass) { (value) in
//                changeTextValue?(value)
//            }
            if isShowPass {
                TextField(placeText, text: binding)
            }else {
                SecureField(placeText, text: binding)
            }
            Image(!isShowPass ? "hidden_password" : "show_password").onTapGesture {
                isShowPass.toggle()
            }
        }.font(Font.system(size: 16)).background(lineBackView(isSelected: false,color: Color(UIColor.secondarySystemBackground))).frame(height: IPHONEDeviceInfo.textFieldHeight, alignment: .center)
        
    }
    
    
}

struct CustomTextField: UIViewRepresentable {
    
    func makeCoordinator() -> Coordinator {
        Coordinator(textField: self)
    }
    
    
    let placeholder: String
    @Binding var text: String
    @Binding var isSecureText: Bool
    let changeTextBlock:((_ value: String)->Void)?
    
    class Coordinator: NSObject,UITextFieldDelegate {
        var baseText: CustomTextField!
        override init() {
            super.init()
        }
        convenience init(textField: CustomTextField) {
            self.init()
            baseText = textField
        }
        func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
            
            var allText = textField.text ?? "";
            if range.location != 0 {
                let startIndex = allText.index(allText.startIndex, offsetBy: range.location);
                let endIndex = allText.index(allText.startIndex, offsetBy: range.upperBound);
                allText.replaceSubrange(.init(uncheckedBounds: (lower: startIndex, upper: endIndex)), with: string)
            }else {
                allText += string
            }
            DispatchQueue.main.async {
                self.baseText.text = allText
                self.baseText.changeTextBlock?(allText)
            }
            return true
        }
    }
    
    func updateUIView(_ uiView: UITextField, context: Context) {
        uiView.isSecureTextEntry = !isSecureText
        uiView.text = text
    }
    func makeUIView(context: Context) -> UITextField {
        let textField = UITextField()
        textField.isSecureTextEntry = !isSecureText
        textField.backgroundColor = UIColor.clear
        textField.placeholder = placeholder
        textField.text = text
        textField.delegate = context.coordinator
        textField.font = UIFont.systemFont(ofSize: 16)
        return textField
    }
}
