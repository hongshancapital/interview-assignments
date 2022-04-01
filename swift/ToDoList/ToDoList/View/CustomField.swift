//
//  CustomField.swift
//  ToDoList
//
//  Created by wh on 2022/1/13.
//

import SwiftUI

struct CustomField: UIViewRepresentable {
    @Binding public var isFirstResponder: Bool
    @Binding public var text: String
    public var shouldReturn: (UITextField) -> () = { (_: UITextField) in }
    
    public init(text: Binding<String>, isFirstResponder: Binding<Bool>, shouldReturn: @escaping (UITextField) -> () = { _ in }) {
        self.shouldReturn = shouldReturn
        self._text = text
        self._isFirstResponder = isFirstResponder
    }
    
    public func makeUIView(context: Context) -> UITextField {
        let view = UITextField()
        view.setContentCompressionResistancePriority(.defaultLow, for: .horizontal)
        view.addTarget(context.coordinator, action: #selector(Coordinator.textViewDidChange), for: .editingChanged)
        view.delegate = context.coordinator
        return view
    }
    
    public func updateUIView(_ uiView: UITextField, context: Context) {
        uiView.text = text
        if isFirstResponder {
            uiView.becomeFirstResponder()
        }else {
            uiView.resignFirstResponder()
        }
    }
    
    public func makeCoordinator() -> Coordinator {
        Coordinator($text, isFirstResponder: $isFirstResponder, shouldReturn: shouldReturn)
    }
    
    public class Coordinator: NSObject, UITextFieldDelegate {
        var text: Binding<String>
        var isFirstResponder: Binding<Bool>
        var shouldReturn: (UITextField) -> ()
        
        init(_ text: Binding<String>, isFirstResponder: Binding<Bool>, shouldReturn: @escaping (UITextField) -> ()) {
            self.shouldReturn = shouldReturn
            self.text = text
            self.isFirstResponder = isFirstResponder
        }
        
        @objc public func textViewDidChange(_ textField: UITextField) {
            text.wrappedValue = textField.text ?? ""
        }
        
        public func textFieldDidBeginEditing(_ textField: UITextField) {
            DispatchQueue.main.async {
                self.isFirstResponder.wrappedValue = true
            }
        }
        
        public func textFieldShouldReturn(_ textField: UITextField) -> Bool {
            DispatchQueue.main.async {
                self.isFirstResponder.wrappedValue = false
            }
            shouldReturn(textField)
            return true
        }
        
        public func textFieldDidEndEditing(_ textField: UITextField) {
            DispatchQueue.main.async {
                self.isFirstResponder.wrappedValue = false
            }
        }
    }
}
