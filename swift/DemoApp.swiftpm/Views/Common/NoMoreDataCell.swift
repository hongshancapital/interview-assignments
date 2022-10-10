//
//  NoMoreDataCell.swift
//  
//
//  Created by 黄磊 on 2022/4/12.
//

import SwiftUI

struct NoMoreDataCell: View {
    var body: some View {
        Text("No more data.")
            .font(.footnote)
            .foregroundColor(Color(UIColor.lightGray))
            .frame(maxWidth: .infinity)
    }
}

struct NoMoreDataCell_Previews: PreviewProvider {
    static var previews: some View {
        NoMoreDataCell()
    }
}
