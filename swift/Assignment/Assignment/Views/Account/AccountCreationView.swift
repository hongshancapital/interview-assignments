//
//  AccountCreationView.swift
//  Assignment
//
//  Created by Tpphha on 2021/2/22.
//

import SwiftUI

struct AccountCreationView: View {
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    @StateObject private var viewModel = AccountCreationViewModel()
    
    private var createAccountButtonDisabled: Bool {
        if viewModel.isLoading {
            return true
        }
        return !viewModel.isValid
    }
    
    var body: some View {
        GeometryReader { geometry in
            VStack {
                TextField("Name", text: $viewModel.username)
                    .scTextFiledStyle()
                    .padding(.top, geometry.size.height * 0.08)
                SecureField("Password", text: $viewModel.password)
                    .scTextFiledStyle()
                SecureField("Repeat Password", text: $viewModel.passwordAgain)
                    .scTextFiledStyle()
                Button(action: viewModel.onCreateAccount) {
                    Text("Create Account")
                }
                .buttonStyle(PrimaryButtonStyle())
                .padding(.horizontal, 20)
                .padding(.top, 44)
                .disabled(createAccountButtonDisabled)
                
                Spacer()
            }
            .padding(.horizontal, 30)
            .toast(isPresented: $viewModel.isLoading, dismissAfter: .infinity) {
                } content: {
                  ToastView("Loading...")
                    .toastViewStyle(IndefiniteProgressToastViewStyle())
                }
        }
        .onAppear() {
            viewModel.dismiss = {
                self.presentationMode.wrappedValue.dismiss()
            }
        }
    }
    
}

struct AccountCreationView_Previews: PreviewProvider {
    static var previews: some View {
        AccountCreationView()
    }
}
