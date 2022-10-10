//
//  NoDataView.swift
//  
//
//  Created by 黄磊 on 2022/4/12.
//

import SwiftUI

struct NoDataView: View {
    var body: some View {
        Text("No Data!\nPull To Refresh!")
            .multilineTextAlignment(.center)
            .frame(maxWidth: .infinity)
            .foregroundColor(.init(UIColor.lightGray))
    }
}

struct NoDataView_Previews: PreviewProvider {
    static var previews: some View {
        NoDataView()
    }
}
