//
//  ConfirmButton.swift
//  Login
//
//  Created by xiwang wang on 2021/8/31.
//

import SwiftUI

struct ConfirmButton: View {
    var title: String
    var action: () -> Void = {}
    
    @Binding var isNomal: Bool
    var body: some View {
        Button(action: action, label: {
            Rectangle()
                .fill(isNomal ? Color("themeGreen"): Color("disableGray"))
                .frame(height: 44, alignment: .center)
                .overlay(Text(title).foregroundColor(.white).bold())
                .cornerRadius(8)
                .padding(EdgeInsets(top: 25, leading: 30, bottom: 0, trailing: 30))
        })
    }
}

struct ConfirmButton_Previews: PreviewProvider {
    @State static var isNomal = false
    static var previews: some View {
        ConfirmButton(title: "Confirm", isNomal: $isNomal)
    }
}
