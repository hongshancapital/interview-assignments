//
//  HomeView.swift
//  AppHunter
//
//  Created by zhang shijie on 2023/5/24.
//

import SwiftUI

struct ErrorView: View {
    typealias ErrorViewActionHandler = () -> Void
    let error: Error
    let handler: ErrorViewActionHandler
    internal init(error: Error, handler: @escaping ErrorView.ErrorViewActionHandler) {
        self.error = error
        self.handler = handler
    }

    var body: some View {
        VStack {
            Image(systemName: "exclamationmark.icloud.fill").foregroundColor(.gray)
                .font(.system(size: 50, weight: .heavy))
                .padding(.bottom, 4)
            Text("Ooops").foregroundColor(.black)
                .font(.system(size: 30, weight: .heavy))
                .multilineTextAlignment(.center)
            Text(error.localizedDescription).foregroundColor(.gray)
                .font(.system(size: 15)).multilineTextAlignment(.center).padding(.vertical, 4)
            Button {
                self.handler()
            } label: {
                Text("Retry")
            }.padding(.vertical, 12).padding(.horizontal, 30).background(Color.blue)
                .foregroundColor(.white).font(.system(size: 15, weight: .heavy)).cornerRadius(10)
        }
    }
}

struct ErrorView_Previews: PreviewProvider {
    static var previews: some View {
        ErrorView(error: APIError.unknown) {}.previewLayout(.sizeThatFits)
    }
}
