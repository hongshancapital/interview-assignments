//
//  FaceGuideView.swift
//  TestSwiftUI
//
//  Created by zhangshouyin on 2023/3/9.
//

import SwiftUI

struct FaceGuideView: View {
    var body: some View {
        NavigationView {
            NavigationLink("红杉面试Demo制作",destination: UserListView())
        }.background(Color(.secondarySystemFill))
    }
}

struct FaceGuideView_Previews: PreviewProvider {
    static var previews: some View {
        FaceGuideView()
    }
}

