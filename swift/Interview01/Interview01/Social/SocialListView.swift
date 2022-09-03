//
//  SocialListView.swift
//  Interview01
//
//  Created by chenzhe on 2022/6/21.
//

import SwiftUI
import Combine

struct SocialListView: View {
    @ObservedObject var dataProvider = SocialDataProvider()
    
    var body: some View {
        List(dataProvider.data) { item in
            SocialListCell(model: item)
        }
        .navigationBarTitle(
            Text("App")
        )
    }
}

struct SocialListView_Previews: PreviewProvider {
    static var previews: some View {
        SocialListView()
    }
}
