//
//  TextFieldAlertViewController.swift
//  ToDoList
//
//  Created by berchan on 2021/12/19.
//

import UIKit
import Combine
import SwiftUI

extension View {
    func textFieldAlert(isPresented: Binding<Bool>,
                      content: @escaping () -> TextFieldAlert) -> some View {
        TextFieldWrapper(isPresented: isPresented,
                         presentingView: self,
                         content: content)
    }
}

struct TextFieldWrapper<PresentingView: View>: View {

  @Binding var isPresented: Bool
  let presentingView: PresentingView
  let content: () -> TextFieldAlert

    var body: some View {
        ZStack {
            if (isPresented) {
                content().dismissable($isPresented)
            }
            presentingView
        }
    }
}

struct TextFieldAlert {

    let title: String
    var message: String? = nil
    @Binding var text: String?
    var isPresented: Binding<Bool>? = nil
    var okAction: (() -> Void)? = nil
    var cancelAction: (() -> Void)? = nil

    // MARK: Modifiers
    func dismissable(_ isPresented: Binding<Bool>) -> TextFieldAlert {
        TextFieldAlert(title: title, message: message, text: $text, isPresented: isPresented, okAction: okAction, cancelAction: cancelAction)
    }
}

extension TextFieldAlert: UIViewControllerRepresentable {

    typealias UIViewControllerType = TextFieldAlertViewController

    func makeUIViewController(context: UIViewControllerRepresentableContext<TextFieldAlert>) -> UIViewControllerType {
        TextFieldAlertViewController(title: title, message: message, text: $text, isPresented: isPresented, okAction: okAction, cancelAction: cancelAction)
    }

    func updateUIViewController(_ uiViewController: UIViewControllerType,
                              context: UIViewControllerRepresentableContext<TextFieldAlert>) {
    }
}

class TextFieldAlertViewController: UIViewController {

    private let alertTitle: String
    private let message: String?
    @Binding private var text: String?
    private var isPresented: Binding<Bool>?
    private var okAction: (() -> Void)?
    private var cancelAction: (() -> Void)?

    private var subscription: AnyCancellable?
    
    
    init(title: String,
         message: String?,
         text: Binding<String?>,
         isPresented: Binding<Bool>?,
         okAction: (() -> Void)?,
         cancelAction: (() -> Void)?) {
        
        self.alertTitle = title
        self.message = message
        self._text = text
        self.isPresented = isPresented
        self.okAction = okAction
        self.cancelAction = cancelAction
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }


    // MARK: - Lifecycle
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        presentAlertController()
    }

    private func presentAlertController() {
        guard subscription == nil else { return } // present only once

        let vc = UIAlertController(title: alertTitle, message: message, preferredStyle: .alert)

        // add a textField and create a subscription to update the `text` binding
        vc.addTextField { [weak self] textField in
            guard let self = self else { return }
            self.subscription = NotificationCenter.default
                .publisher(for: UITextField.textDidChangeNotification, object: textField)
                .map { ($0.object as? UITextField)?.text ?? "" }
                .assign(to: \.text, on: self)
        }

   
        let cancelAction = UIAlertAction(title: "取消", style: .cancel) { [weak self] _ in
            self?.isPresented?.wrappedValue = false
            self?.cancelAction?()
        }
        vc.addAction(cancelAction)
        
        
        let okAction = UIAlertAction(title: "确认", style: .default) { [weak self] _ in
            self?.isPresented?.wrappedValue = false
            self?.okAction?()
        }
        vc.addAction(okAction)
        
        present(vc, animated: true, completion: nil)
    }
}
    
