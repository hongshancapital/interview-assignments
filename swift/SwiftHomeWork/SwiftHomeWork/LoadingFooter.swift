//
//  LoadingFooter.swift
//  SwiftHomeWork
//
//  Created by apple on 2022/4/13.
//

import SwiftUI

struct LoadingFooter: View {
    var isEnd = false
    var body: some View {
        HStack{
            Spacer()
            if isEnd{
                Text("No more data.")
            }else{
                HStack.init(alignment: .center, spacing: 10, content: {
                    ProgressView()
                    Text("Loading...")
                })
            }
            Spacer()
        }
        .foregroundColor(.gray)
    }
}

struct LoadingFooter_Previews: PreviewProvider {
    static var previews: some View {
        VStack{
            LoadingFooter(isEnd: false)
            LoadingFooter(isEnd: true)
        }
    }
}
