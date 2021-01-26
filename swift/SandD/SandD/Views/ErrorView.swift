//
//  ErrorView.swift
//  SandD
//
//  Created by qiu on 2021/1/22.
//

import SwiftUI

struct ErrorView: View {
    var error: String
    var body: some View {
        VStack {
            Text(error)
                .font(.footnote)
                .fontWeight(.thin)
                .foregroundColor(.secondary)
                .padding(.top, 70)
            Spacer()
        }
    }
}

struct ErrorView_Previews: PreviewProvider {
    static var previews: some View {
        ErrorView(error: "Error occured on requesting")
    }
}
