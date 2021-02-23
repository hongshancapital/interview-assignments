//
//  ProfileView.swift
//  Assignment
//
//  Created by Tpphha on 2021/2/22.
//

import SwiftUI

struct ProfileView: View {
    
    @StateObject private var viewModel = ProfileViewModel()
    @State private var showingConfirmLogoutSheet: Bool = false
    
    private var logoutButtonDisabled: Bool {
        return viewModel.isLoading
    }
    
    var body: some View {
        VStack {
            if let user = viewModel.user {
                ProfileHeader(user: user)
            }
            Spacer()
            Button(action: {
                showingConfirmLogoutSheet = true
            }) {
                Text("Logout")
            }
            .buttonStyle(DestructiveButtonStyle())
            .padding(.horizontal, 49)
            .padding(.bottom, 49)
            .disabled(logoutButtonDisabled)
        }
        .ignoresSafeArea(.all, edges: .top)
        .toast(isPresented: $viewModel.isLoading, dismissAfter: .infinity) {
            return
                ToastView("Logging out...")
                    .toastViewStyle(IndefiniteProgressToastViewStyle())
        }
        .actionSheet(isPresented: $showingConfirmLogoutSheet, content: {
            ActionSheet(
                title: Text("Are you sure you want to log out?"),
                buttons: [.cancel(Text("Cancel")),
                          .destructive(Text("Logout"),
                                       action: viewModel.onLogout)
                ]
            )
        })
    }
}

struct ProfileView_Previews: PreviewProvider {
    static var previews: some View {
        ProfileView()
            .preferredColorScheme(.dark)
            .environmentObject(AppState())
    }
}
