//
//  NoMoreDataView.swift
//  swiftUI-demo
//
//  Created by guo changqing on 2023/3/24.
//

import SwiftUI

struct NoMoreDataView: View {
    var body: some View {
        HStack(alignment: .center) {
            Spacer()
            Text("No More Data...").font(.system(size: 18)).foregroundColor(.gray)
            Spacer()
        }
    }
}

struct NoMoreDataView_Previews: PreviewProvider {
    static var previews: some View {
        NoMoreDataView()
    }
}
