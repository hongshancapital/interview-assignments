//
//  LoadingMoreCell.swift
//  
//
//  Created by 黄磊 on 2022/4/12.
//

import SwiftUI

struct LoadingMoreCell: View {
    var body: some View {
        HStack(spacing: 6) {
            Spacer()
            LoadingView()
            Text("Loading...")
                .font(.footnote)
                .foregroundColor(Color(UIColor.lightGray))
            Spacer()
        }
    }
}

struct LoadingMoreCell_Previews: PreviewProvider {
    static var previews: some View {
        LoadingMoreCell()
    }
}
