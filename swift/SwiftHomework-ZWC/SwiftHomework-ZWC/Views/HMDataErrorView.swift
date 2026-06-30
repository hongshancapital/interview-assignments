//
//  HMDataErrorView.swift
//  SwiftHomework-ZWC
//
//  Created by 油菜花 on 2022/2/16.
//

import SwiftUI

struct HMDataErrorView: View {
    var textMsg: String
    var imageName: String
    var body: some View {
        VStack {
            Image(imageName)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 80, height: 80)
                .padding()
            Text(textMsg)
                .multilineTextAlignment(.center)
                .padding(.bottom, 20)
        }
        
    }
}
