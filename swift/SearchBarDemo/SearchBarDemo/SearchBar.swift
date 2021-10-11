//
//  SearchListView.swift
//  SearchBarDemo
//
//  Created by Jaydon Qin on 2021/9/28.
//

import SwiftUI

struct SearchBar : UIViewRepresentable {
  @Binding var text : String
    @Binding var isEditing : Bool

  func searchBar(_ searchBar: UISearchBar,
                 textDidChange searchText: String) {
    text = searchText
  }
    func searchBarShouldBeginEditing(_ searchBar: UISearchBar) -> Bool {
        isEditing = true
        return true
    }
    func searchBarTextDidBeginEditing(_ searchBar: UISearchBar) {
        isEditing = false
    }
  func makeCoordinator() -> Cordinator {
    return Cordinator(text: $text,isEditing: $isEditing)
  }
  func makeUIView(context: UIViewRepresentableContext<SearchBar>)
    -> UISearchBar {
      
      let searchBar = UISearchBar(frame: .zero)
      searchBar.layer.borderWidth = 1
      searchBar.layer.borderColor = UIColor.white.cgColor
    searchBar.backgroundColor = UIColor.gray
      searchBar.delegate = context.coordinator
    searchBar.placeholder = "Tap here to search"
      return searchBar
  }
  func updateUIView(_ uiView: UISearchBar,
                    context: UIViewRepresentableContext<SearchBar>) {
    uiView.text = text
  }
  
  class Cordinator : NSObject, UISearchBarDelegate {
    @Binding var isEditing : Bool
    @Binding var text : String
    init(text : Binding<String>,isEditing : Binding<Bool>)
    {
      _isEditing = isEditing
      _text = text
    }
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String)
    {
      text = searchText
    }
  
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
      searchBar.endEditing(true)
    }
    func searchBarCancelButtonClicked(_ searchBar: UISearchBar) {
        searchBar.text = ""
    }
    func searchBarShouldBeginEditing(_ searchBar: UISearchBar) -> Bool {
//        searchBar.frame = CGRect.init(x: searchBar.frame.minX, y: searchBar.frame.minY - 100, width: searchBar.frame.width, height: searchBar.frame.height)
        isEditing = true
        return true
    }
    func searchBarTextDidEndEditing(_ searchBar: UISearchBar) {
        isEditing = false
    }
  }
}



struct SearchBar_Previews: PreviewProvider {
  static var previews: some View {

    SearchBar(text: .constant("Hello"), isEditing: .constant(false))
  }
}
