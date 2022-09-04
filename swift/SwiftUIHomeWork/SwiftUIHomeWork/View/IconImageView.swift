//
//  IconImageView.swift
//  SwiftUIHomeWork
//
//  Created by quanwei chen on 2022/9/4.
//

import SwiftUI

struct IconImageView: View {
    var url: String = ""
    var body: some View {
        AsyncImage(url: URL(string: url), content: { image in
            image.resizable()
        }, placeholder: {
            ProgressView()
        })
        .cornerRadius(10)
        .frame(width: 50, height: 50, alignment: Alignment.center)
    }
}

struct IconImageView_Previews: PreviewProvider {
    static var previews: some View {
        IconImageView(url: "")
        
    }
}
