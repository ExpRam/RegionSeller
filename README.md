# RegionSeller

A plugin that allows you to sell regions from the WorldGuard plugin

# Commands

| Command | Description |
| ------ | ------ |
| /rgbuy | Open the menu with the regions being sold |
| /rgsell sell rgname price | Sell a region |
| /rgsell remove rgname | Remove a region from sale |
| /adminrg reload | Reload config |

# Permissions

| Command | Description |
| ------ | ------ |
| regions.player_command | /rgbuy and /rgsell command |
| regions.admin_command | /adminrg command |

# Config

```yml
messages:
  no_perms: "&cYou don't have a permission!"
  not_a_player: "&cYou're not a player!"
  usage_sell: "&e/regions &asell <region_name> <price>"
  usage_remove: "&e/regions &aremove <region_name>"
  usage_reload: "&e/adminregions &areload"
  config_reloaded: "&aThe plugin has been successfully reloaded!"
  invalid_number: "&cInvalid Number!"
  region_not_found: "&cRegion {region} not found! Maybe you are not in the same world with the region!"
  not_an_owner: "&cYou are not the owner of the {region} region!"
  already_for_sale: "&cRegion {region} already for sale"
  region_sold: "&aRegion {region} successfully put up for sale at the price of {price} coins"
  removed_from_sale: "&aRegion {region} has been successfully removed from sale"
  region_not_sale: "&cIt is impossible to remove a region {region} from sale since it is not for sale!"
  already_on_first_page: "&cYou are already on the first page!"
  on_last_page: "&cYou are on the last page!"
  notenoughmoney: "&cNot enough money"
  region_purchased: "&aYou have successfully purchased the region {region} at the price {price}!"
  already_sold: "&cRegion {region} already sold!"
  cannot_teleport: "&cIt is impossible to teleport to this region!"
  region_was_deleted: "&cThe region was not found because it was deleted!"
  inventory:
    close: "&cClose"
    right: "&cRight"
    left: "&cLeft"
    invtitle: "&cRegions"
    rgitemtitle: "&aRegion {region}"
    tptoregion: "&aRMB &efor teleportation"
    clicktobuy: "&aLMB &efor purchase"
    rgitemlore:
      - "&aSeller: &e{seller}"
      - "&aPrice: &e{price}"
      - "&aRegion name: &e{region}"
#Gives permission to teleport to the region
tp_to_region: true
#Enables safe teleportation (Prevents you from dying in blocks)
safe_tp: true
```
