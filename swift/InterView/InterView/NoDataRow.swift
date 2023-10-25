//
//  NoDataRow.swift
//  InterView
//
//  Created by 黑旭鹏 on 2022/4/5.
//

import SwiftUI

struct NoDataRow: View {
    var body: some View {
        ProgressView()
            .frame(width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height, alignment: .center)
            .listRowBackground(Color("customColor"))
    }
}

struct NoDataRow_Previews: PreviewProvider {
    static var previews: some View {
        NoDataRow()
    }
}
