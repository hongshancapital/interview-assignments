//
//  NoMoreDataView.swift
//  MyApps
//
//  Created by liangchao on 2022/4/17.
//

import SwiftUI

struct NoMoreDataView: View {
    var body: some View {
        Text("No more data.")
            .font(.footnote)
            .foregroundColor(Color.gray)
            .frame(maxWidth: .infinity)
    }
}

struct NoMoreDataView_Previews: PreviewProvider {
    static var previews: some View {
        NoMoreDataView()
    }
}
