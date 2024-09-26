//
//  ErrorView.swift
//  MyApps
//
//  Created by liangchao on 2022/4/17.
//

import SwiftUI

struct ErrorMessage {
    let title: String = "Sorry,Something Went Error"
    let detail: String = "Click Retry To Try To Recover"
}

struct ErrorView: View {
    let error: ErrorMessage
    let retryAction: () -> Void
    
    var body: some View {
        VStack {
            Text(error.title)
                .font(.title)
            Text(error.detail)
                .font(.callout)
                .multilineTextAlignment(.center)
                .padding(.bottom, 40).padding()
            Button(action: retryAction, label: { Text("Retry").bold() })
        }
    }
}


struct ErrorView_Previews: PreviewProvider {
    static var previews: some View {
        ErrorView(error: ErrorMessage(),
                  retryAction: { })
    }
}

