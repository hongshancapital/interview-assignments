//
//  FirstResponderTextField.swift
//  scdt-homework
//
//  Created by Chr1s on 2021/9/27.
//

import Foundation
import SwiftUI

// MARK: - Custom TextField with First Responder
struct FirstResponderTextField: UIViewRepresentable {

    typealias UIViewType = UITextField
    
    @Binding var text: String
    let placeholder: String
    var onCommit: (()->Void)
    
    class Coordinator: NSObject, UITextFieldDelegate {
        
        @Binding var text: String
        var becameFirstResponder = false
        var parent: FirstResponderTextField
        init(text: Binding<String>, _ uiTextField: FirstResponderTextField) {
            self._text = text
            self.parent = uiTextField
        }
        
        func textFieldDidChangeSelection(_ textField: UITextField) {
            print("didchange")
            text = textField.text ?? ""
        }
        
        func textFieldDidEndEditing(_ textField: UITextField) {
            parent.onCommit()
        }
        
        func textFieldShouldReturn(_ textField: UITextField) -> Bool {
            textField.resignFirstResponder()
        }
    }
    
    func makeUIView(context: Context) -> UITextField {
        let textField = UITextField()
        textField.delegate = context.coordinator
        textField.placeholder = placeholder
        
        textField.autocorrectionType = .no
        textField.autocapitalizationType = .none
        return textField
    }
    
    func makeCoordinator() -> Coordinator {
        return Coordinator(text: $text, self)
    }
    
    func updateUIView(_ uiView: UIViewType, context: Context) {
        if !context.coordinator.becameFirstResponder {
            uiView.becomeFirstResponder()
            context.coordinator.becameFirstResponder = true
        }
    }
}

