//
//  TextView.swift
//  memo
//
//  Created by LI on 2021/11/14.
//

import SwiftUI

struct TextView: UIViewRepresentable {
    
    typealias UIViewType = UITextView
    
    @Binding var text: String
    @Binding var edit: Bool
    var onCommit: (() -> Void)? = nil
    
    func makeUIView(context: Context) -> UITextView {
        let textView = UITextView()
        textView.isUserInteractionEnabled = true
        textView.isSelectable = true
        textView.delegate = context.coordinator
        textView.backgroundColor = UIColor.yellow
        return textView
    }
    
    func updateUIView(_ uiView: UITextView, context: Context) {
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
    
    func makeCoordinator() -> Coordinator {
        Coordinator($text, commit: onCommit)
    }
    
    class Coordinator: NSObject, UITextViewDelegate {
        
        init(_ text: Binding<String>, commit: (() -> Void)? = nil) {
            self.text = text
            self.commit = commit
        }
        
        var text: Binding<String>
        var commit: (() -> Void)?
        
        func textViewDidChange(_ textView: UITextView) {
            text.wrappedValue = textView.text
        }
        
        func textViewDidEndEditing(_ textView: UITextView) {
            commit?()
        }
    }
}


//struct TextView_Previews: PreviewProvider {
//    static var previews: some View {
//        TextView()
//    }
//}
