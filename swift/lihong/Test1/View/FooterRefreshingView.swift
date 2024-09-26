//
//  FooterRefreshingView.swift
//  Test1
//
//  Created by Hong Li on 2022/10/19.
//

import Foundation
import SwiftUI

struct FooterRefreshingView: View {
    var body: some View {
        HStack {
            ProgressView()
                .padding(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 2))

            Text("Loading...")
                .font(.system(size: 16))
                .foregroundColor(Color(.lightGray))
        }
    }
}

struct FooterRefreshingView_Previews: PreviewProvider {
    static var previews: some View {
        FooterRefreshingView()
    }
}
