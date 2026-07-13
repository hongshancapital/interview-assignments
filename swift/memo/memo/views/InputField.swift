//
//  InputField.swift
//  memo
//
//  Created by LI on 2021/11/15.
//

import SwiftUI

typealias InputFieldHandler = (String) -> Void

struct InputField: UIViewRepresentable {
    typealias UIViewType = UITextField
    
    var placeholder: String = ""
    
    @Binding var text: String
    @Binding var edit: Bool
    
    var commit: InputFieldHandler? = nil
    var change: InputFieldHandler? = nil
    
    func makeUIView(context: Context) -> UITextField {
        let textField = UITextField()
        textField.delegate = context.coordinator
        textField.placeholder = placeholder
        return textField
    }
    
    func updateUIView(_ uiView: UITextField, context: Context) {
        uiView.text = text
        if edit && uiView.isFirstResponder == false {
            DispatchQueue.main.async {
                uiView.becomeFirstResponder()
            }
        }
        else if edit == false && uiView.isFirstResponder {
            DispatchQueue.main.async {
                uiView.resignFirstResponder()
            }
        }
    }
    
    internal func makeCoordinator() -> Coordinator {
        let coor = Coordinator($text, commit: commit, change: change)
        coor.addTextChangeObserver()
        return coor
    }
}

internal class Coordinator: NSObject {
    var text: Binding<String>
    var commit: InputFieldHandler?
    var change: InputFieldHandler?
    
    init(_ text: Binding<String>, commit: InputFieldHandler? = nil, change: InputFieldHandler? = nil) {
        self.text = text
        self.commit = commit
        self.change = change
    }
    
    func addTextChangeObserver() {
        NotificationCenter.default.addObserver(self, selector: #selector(textDidChangeNotification), name: UITextField.textDidChangeNotification, object: nil)
    }
}

extension Coordinator: UITextFieldDelegate {
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        commit?(text.wrappedValue)
        return true
    }
    
    @objc func textDidChangeNotification(_ noti: Notification) {
        guard let textField = noti.object as? UITextField else {
            return
        }
        let value = textField.text ?? ""
        text.wrappedValue = value
        change?(value)
    }
}

/// MARK: Preview
struct InputField_Previews: PreviewProvider {
    
    @State static var edit: Bool = false
    @State static var text: String = ""
    
    static var previews: some View {
        InputField(text: $text, edit: $edit)
    }
}
