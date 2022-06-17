//
//  FooterLoadingView.swift
//  MyApps
//
//  Created by liangchao on 2022/4/17.
//

import SwiftUI

struct FooterLoadingView: View {
    var body: some View {
        return  HStack(spacing: 6) {
            Spacer()
            ActivityIndicatorView()
            Text("Loading...")
                .font(.footnote)
                .foregroundColor(Color(UIColor.lightGray))
            Spacer()
        }
    }
}

struct FooterLoadingView_Previews: PreviewProvider {
    static var previews: some View {
        FooterLoadingView()
    }
}
